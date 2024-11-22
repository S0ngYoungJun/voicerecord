package com.example.voicenote.controller;

import com.example.voicenote.model.Note;
import com.example.voicenote.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
    // 새로운 노트를 저장
    @PostMapping
    public ResponseEntity<Note> saveNote(@RequestBody String content) {
        logger.info("새로운 노트 저장 요청: content={}", content);
        if (content == null || content.trim().isEmpty()) {
            logger.warn("요청 본문이 비어 있습니다.");
            return ResponseEntity.badRequest().body(null);
        }
        Note savedNote = noteService.saveNote(content);
        logger.info("노트 저장 완료: {}", savedNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        logger.info("모든 노트 조회 요청");
        List<Note> notes = noteService.getAllNotes();
        logger.info("조회된 노트 개수: {}", notes.size());
        return ResponseEntity.ok(notes);
    }

    // ID로 특정 노트 조회
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Note>> getNoteById(@PathVariable UUID id) {
        logger.info("특정 노트 조회 요청: id={}", id);
        Optional<Note> note = noteService.getNoteById(id);
        if (note.isPresent()) {
            logger.info("노트 조회 성공: {}", note.get());
            return ResponseEntity.ok(note);
        } else {
            logger.warn("노트 조회 실패: 해당 ID의 노트가 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
        }
    }


    // ID로 특정 노트 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable UUID id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

    // 텍스트 저장
    @PostMapping("/save-text")
    public ResponseEntity<Note> saveText(@RequestBody Map<String, String> payload) {
        logger.info("텍스트 저장 요청: payload={}", payload);
        String content = payload.get("content");
        if (content == null || content.trim().isEmpty()) {
            logger.warn("content 필드가 비어 있습니다.");
            return ResponseEntity.badRequest().body(null);
        }
        Note savedNote = noteService.saveNote(content);
        logger.info("텍스트 저장 완료: {}", savedNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
    }

    // 음성 파일 변환
    @PostMapping("/transcribe")
    public ResponseEntity<Map<String, String>> transcribeAudio(@RequestParam("file") MultipartFile file) {
        logger.info("음성 파일 변환 요청: fileName={}, size={}", file.getOriginalFilename(), file.getSize());
        if (file.isEmpty()) {
            logger.warn("전송된 파일이 비어 있습니다.");
            return ResponseEntity.badRequest().body(Map.of("error", "파일이 비어 있습니다."));
        }
        try {
            /*  TODO: 음성 파일 변환 로직 추가 (외부 API 호출 또는 로컬 변환 구현)*/
            String transcribedText = "변환된 예제 텍스트";
            logger.info("파일 변환 성공: transcribedText={}", transcribedText);

            // 응답 생성
            Map<String, String> response = new HashMap<>();
            response.put("transcribedText", transcribedText);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("음성 파일 변환 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Map.of("error", "음성 파일 변환 중 오류가 발생했습니다."));
        }
}

}
