import axios from 'axios';

const API_URL = 'api/notes';

const handleError = (error, action = "요청 처리") => {
    console.error(`${action} 중 오류가 발생했습니다:`, error.response?.data || error.message || error);
    throw error;
};

// 노트를 저장하는 함수
export const saveNote = async (content) => {
    console.log("노트 저장 요청: ", { content });
    try {
        const response = await axios.post(API_URL, { content }, {
            headers: {
                'Content-Type': 'application/json',
            },
        });
        console.log("노트 저장 성공: ", response.data);
        return response.data;
    } catch (error) {
        handleError(error, "노트를 저장");
    }
};

// 모든 노트를 가져오는 함수
export const getAllNotes = async () => {
    console.log("모든 노트 조회 요청");
    try {
        const response = await axios.get(API_URL);
        console.log("모든 노트 조회 성공: ", response.data);
        return response.data;
    } catch (error) {
        handleError(error, "모든 노트를 조회");
    }
};

// 특정 ID의 노트를 가져오는 함수
export const getNoteById = async (id) => {
    console.log(`ID가 ${id}인 노트 조회 요청`);
    try {
        const response = await axios.get(`${API_URL}/${id}`);
        console.log(`ID가 ${id}인 노트 조회 성공: `, response.data);
        return response.data;
    } catch (error) {
        handleError(error, `ID가 ${id}인 노트를 조회`);
    }
};

// 특정 ID의 노트를 삭제하는 함수
export const deleteNoteById = async (id) => {
    console.log(`ID가 ${id}인 노트 삭제 요청`);
    try {
        const response = await axios.delete(`${API_URL}/${id}`);
        console.log(`ID가 ${id}인 노트 삭제 성공`);
        return response.data;
    } catch (error) {
        handleError(error, `ID가 ${id}인 노트를 삭제`);
    }
};
