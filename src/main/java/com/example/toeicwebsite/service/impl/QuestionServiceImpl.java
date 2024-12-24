package com.example.toeicwebsite.service.impl;

import com.example.toeicwebsite.data.dto.*;
import com.example.toeicwebsite.data.entity.*;
import com.example.toeicwebsite.data.repository.AnswerRepository;
import com.example.toeicwebsite.data.repository.QuestionRepository;
import com.example.toeicwebsite.data.repository.TestDetailRepository;
import com.example.toeicwebsite.data.repository.TopicRepository;
import com.example.toeicwebsite.exception.ConflictException;
import com.example.toeicwebsite.exception.ResourceNotFoundException;
import com.example.toeicwebsite.service.QuestionService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TestDetailRepository testDetailRepository;
    @Override
    public List<QuestionDTO> getAllQuestionsWithAnswersByTopicId(Long topicId) {
        List<Question> questions = questionRepository.findAllByTopicId(topicId);
        return questions.stream().map(this::questionConvertToDTO).collect(Collectors.toList());
    }
    @Override
    public QuestionDTO getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("question id nay khong ton tai", questionId))
        );
        return questionConvertToDTO(question);
    }

    public List<AnswerDTO> getAllAnswerByQuestionId(Long questionId) {
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        return answers.stream().map(this::answerConvertToDTO).collect(Collectors.toList());
    }

    private QuestionDTO questionConvertToDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setName(question.getName());
        questionDTO.setTopicId(question.getTopic().getId());
        List<AnswerDTO> answerDTOs = getAllAnswerByQuestionId(question.getId());
        questionDTO.setAnswers(answerDTOs);

        return questionDTO;
    }

    private AnswerDTO answerConvertToDTO(Answer answer) {
        AnswerDTO answerDTO = new AnswerDTO();

        answerDTO.setId(answer.getId());
        answerDTO.setContent(answer.getContent());
        answerDTO.setCorrectAnswer(answer.isCorrectAnswer());
        answerDTO.setQuestionId(answer.getQuestion().getId());

        return answerDTO;
    }
    //lấy danh sách questionID trong 1 topic
    @Override
    public List<Long> getAllQuestionsIDByTopicId(Long topicId) {
        List<Question> questions = questionRepository.findAllByTopicId(topicId);

        return questions.stream().map(Question::getId).collect(Collectors.toList());
    }

    @Override
    public PaginationDTO filterQuestion(String keyword, int pageNumber, int pageSize) {
        Page<Question> page = questionRepository.filterQuestion(keyword, PageRequest.of(pageNumber, pageSize));
        List<QuestionDTO> list = new ArrayList<>();

        for (Question question: page.getContent()) {

//            SkillDTO skillDTO = skillMapper.toDTO(skill);
            QuestionDTO questionDTO = new QuestionDTO();

            questionDTO.setId(question.getId());
            questionDTO.setName(question.getName());

            list.add(questionDTO);
        }
        return new PaginationDTO(list, page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    @Override
    public MessageResponse createQuestion(QuestionDTO questionDTO) {
        Topic topic = topicRepository.findById(questionDTO.getTopicId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "topic khong ton tai"))
        );

        Optional<Question> questionFind = questionRepository.findByName(questionDTO.getName());
        if (questionFind.isPresent()){
            throw new ConflictException(Collections.singletonMap("cau hoi nay da ton tai: ", questionDTO.getName()));
        }
        else {
            Question question = new Question();
            question.setName(questionDTO.getName());
            question.setTopic(topic);

            questionRepository.save(question);

            List<AnswerDTO> answerDTOs = questionDTO.getAnswers();
            for (AnswerDTO answerDTO: answerDTOs) {
                Answer answer = new Answer();

                answer.setContent(answerDTO.getContent());
                answer.setCorrectAnswer(answerDTO.isCorrectAnswer());
                answer.setQuestion(question);

                answerRepository.save(answer);
            }
            return new MessageResponse(HttpServletResponse.SC_OK, "tao question thanh cong");
        }
    }

    @Override
    public MessageResponse deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("question id nay khong ton tai", questionId))
        );
        List<TestDetail> testDetails = testDetailRepository.findAllByQuestionId(questionId);
        if(!testDetails.isEmpty()){
            throw new ConflictException(Collections.singletonMap("da ton tai test detail", questionId));
        }

        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        answerRepository.deleteAll(answers);
        questionRepository.delete(question);

        return new MessageResponse(HttpServletResponse.SC_OK, "xoa question and answer thanh cong");
    }

    @Override
    public void importQuestionsFromExcel(MultipartFile file) throws Exception {
        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            throw new IllegalArgumentException("Định dạng file không hợp lệ. Vui lòng tải lên file Excel (.xlsx).");
        }

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Bỏ qua hàng tiêu đề
                    continue;
                }

                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setName(row.getCell(0).getStringCellValue()); // Tên câu hỏi
                questionDTO.setTopicId((long) row.getCell(1).getNumericCellValue()); // ID topic

                List<AnswerDTO> answers = new ArrayList<>();
                for (int i = 2; i < row.getLastCellNum(); i += 2) {
                    if (row.getCell(i) == null || row.getCell(i + 1) == null) break;

                    AnswerDTO answerDTO = new AnswerDTO();
                    answerDTO.setContent(row.getCell(i).getStringCellValue()); // Nội dung câu trả lời
                    answerDTO.setCorrectAnswer(row.getCell(i + 1).getBooleanCellValue()); // Đúng/Sai
                    answers.add(answerDTO);
                }
                questionDTO.setAnswers(answers);

                // Gọi phương thức để lưu câu hỏi
                createQuestion(questionDTO);
            }
        }
    }
}
