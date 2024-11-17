import axios from 'axios';

const API_URL = 'api/notes';

// 노트를 저장하는 함수
export const saveNote = async (content) => {
    try {
        const response = await axios.post(API_URL, { content }, {
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error("노트를 저장하는 중 오류가 발생했습니다:", error.response || error.message);
        throw error;
    }
};

// 모든 노트를 가져오는 함수
export const getAllNotes = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error("노트를 가져오는 중 오류가 발생했습니다:", error.response || error.message);
        throw error;
    }
};

// 특정 ID의 노트를 가져오는 함수
export const getNoteById = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/${id}`);
        return response.data;
    } catch (error) {
        console.error(`ID가 ${id}인 노트를 가져오는 중 오류가 발생했습니다:`, error.response || error.message);
        throw error;
    }
};

// 특정 ID의 노트를 삭제하는 함수
export const deleteNoteById = async (id) => {
    try {
        const response = await axios.delete(`${API_URL}/${id}`);
        return response.data;
    } catch (error) {
        console.error(`ID가 ${id}인 노트를 삭제하는 중 오류가 발생했습니다:`, error.response || error.message);
        throw error;
    }
};
