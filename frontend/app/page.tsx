"use client";

import { useState, useRef } from "react";
import axios from "axios";
import Link from "next/link";

export default function HomePage() {
    const [isRecording, setIsRecording] = useState(false);
    const [audioBlob, setAudioBlob] = useState<Blob | null>(null);
    const [transcribedText, setTranscribedText] = useState(""); // 변환된 텍스트
    const [uploadStatus, setUploadStatus] = useState(""); // 업로드 상태
    const mediaRecorderRef = useRef<MediaRecorder | null>(null);

    const handleStartRecording = async () => {
        try {
            const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
            const mediaRecorder = new MediaRecorder(stream);
            mediaRecorderRef.current = mediaRecorder;

            const audioChunks: Blob[] = [];
            mediaRecorder.ondataavailable = (event) => {
                audioChunks.push(event.data);
            };

            mediaRecorder.onstop = () => {
                const audioBlob = new Blob(audioChunks, { type: "audio/wav" });
                setAudioBlob(audioBlob);
            };

            mediaRecorder.start();
            setIsRecording(true);
        } catch (error) {
            console.error("마이크 접근 실패:", error);
        }
    };

    const handleStopRecording = () => {
        if (mediaRecorderRef.current) {
            mediaRecorderRef.current.stop();
            setIsRecording(false);
        }
    };

    const handleTranscribe = async () => {
        if (!audioBlob) {
            alert("변환할 녹음 파일이 없습니다.");
            return;
        }

        try {
            const formData = new FormData();
            formData.append("file", audioBlob, "recording.wav");

            const response = await axios.post("http://localhost:8080/api/transcribe", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });

            setTranscribedText(response.data.transcribedText); // 변환된 텍스트 설정
        } catch (error) {
            console.error("텍스트 변환 중 오류 발생:", error);
            alert("텍스트 변환 실패");
        }
    };

    const handleSaveToDatabase = async () => {
        if (!transcribedText) {
            alert("저장할 텍스트가 없습니다.");
            return;
        }

        try {
            const response = await axios.post("http://localhost:8080/api/save-text", {
                text: transcribedText,
            });

            setUploadStatus("DB 저장 성공!");
            console.log("서버 응답:", response.data);
        } catch (error) {
            console.error("DB 저장 중 오류 발생:", error);
            setUploadStatus("DB 저장 실패!");
        }
    };

    return (
        <div>
            <h1>환영합니다!</h1>
            <p>Voice Note 애플리케이션에 오신 것을 환영합니다.</p>
            <Link href="/notes">
                <button>노트 목록 보기</button>
            </Link>

            <div style={{ marginTop: "2rem" }}>
                <h2>보이스 녹음</h2>
                <button onClick={isRecording ? handleStopRecording : handleStartRecording}>
                    {isRecording ? "녹음 중지" : "녹음 시작"}
                </button>

                {audioBlob && (
                    <div style={{ marginTop: "1rem" }}>
                        <button onClick={handleTranscribe}>텍스트 변환</button>
                    </div>
                )}

                {transcribedText && (
                    <div style={{ marginTop: "1rem" }}>
                        <h3>변환된 텍스트:</h3>
                        <p>{transcribedText}</p>
                        <button onClick={handleSaveToDatabase}>DB에 저장</button>
                    </div>
                )}
                {uploadStatus && <p>{uploadStatus}</p>}
            </div>
        </div>
    );
}
