// backend/src/main/java/com/example/voicenote/model/Note.java
package com.example.voicenote.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.util.UUID;

@Entity
public class Note {

    @Id
    private UUID id;

    private String content;

    public Note() {}

    public Note(String content) {
        this.content = content;
    }

    // Note가 저장되기 전에 UUID를 자동으로 생성하도록 설정
    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    // Getter & Setter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
