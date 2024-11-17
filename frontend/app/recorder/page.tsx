// app/recorder/page.tsx

"use client";

import { useState, useRef } from "react";

export default function Recorder() {
  const [isRecording, setIsRecording] = useState(false);
  const [audioUrl, setAudioUrl] = useState("");
  const mediaRecorderRef = useRef(null);

  const handleStartRecording = async () => {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
      const mediaRecorder = new MediaRecorder(stream);
      mediaRecorderRef.current = mediaRecorder;

      const audioChunks = [];
      mediaRecorder.ondataavailable = (event) => {
        audioChunks.push(event.data);
      };

      mediaRecorder.onstop = () => {
        const audioBlob = new Blob(audioChunks, { type: "audio/wav" });
        const url = URL.createObjectURL(audioBlob);
        setAudioUrl(url);
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

  return (
    <div>
      <h1>녹음기</h1>
      <button onClick={isRecording ? handleStopRecording : handleStartRecording}>
        {isRecording ? "녹음 중지" : "녹음 시작"}
      </button>
      {audioUrl && (
        <div>
          <h2>녹음된 파일:</h2>
          <audio controls src={audioUrl}></audio>
        </div>
      )}
    </div>
  );
}
