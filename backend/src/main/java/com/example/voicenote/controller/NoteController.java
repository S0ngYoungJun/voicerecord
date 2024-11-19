package com.example.voicenote.controller;

import com.example.voicenote.model.Note;
import com.example.voicenote.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // 새로운 노트를 저장
    @PostMapping
    public ResponseEntity<Note> saveNote(@RequestBody String content) {
        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Note savedNote = noteService.saveNote(content);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
    }

    // 모든 노트 조회
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    // ID로 특정 노트 조회
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Note>> getNoteById(@PathVariable UUID id) {
        Optional<Note> note = noteService.getNoteById(id);
        if (note.isPresent()) {
            return ResponseEntity.ok(note);
        } else {
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
        String content = payload.get("content");
        if (content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Note savedNote = noteService.saveNote(content);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
    }

    // 음성 파일 변환
    @PostMapping("/transcribe")
    public ResponseEntity<Map<String, String>> transcribeAudio(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "파일이 비어 있습니다."));
        }
        try {
            // 변환 로직 (외부 API 또는 로컬 로직으로 대체 필요)
            String transcribedText = "변환된 예제 텍스트";

            // 응답 생성
            Map<String, String> response = new HashMap<>();
            response.put("transcribedText", transcribedText);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "음성 파일 변환 중 오류가 발생했습니다."));
        }
    }
}
