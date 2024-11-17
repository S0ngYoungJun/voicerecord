"use client";

import React, { useEffect, useState } from "react";
import { getAllNotes } from "../utils/api";

export default function NotesPage() {
    const [notes, setNotes] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchNotes = async () => {
            try {
                const fetchedNotes = await getAllNotes();
                setNotes(fetchedNotes);
            } catch (err) {
                console.error("노트를 가져오는 중 오류가 발생했습니다:", err);
                setError("노트를 불러올 수 없습니다.");
            }
        };

        fetchNotes();
    }, []);

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h1>노트 목록</h1>
            <ul>
                {notes.map((note) => (
                    <li key={note.id}>
                        {note.content} (작성일: {new Date(note.createdAt).toLocaleString()})
                    </li>
                ))}
            </ul>
        </div>
    );
}
