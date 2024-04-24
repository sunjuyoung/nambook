//import axios from "axios";
import jwtAxios from "../util/jwtUtil";

export const API_SERVER_HOST = "http://localhost:8080";

const prefix = `${API_SERVER_HOST}/api/todo`;

//조회
export const getOne = async (tno) => {
  const res = await jwtAxios.get(`${prefix}/${tno}`);

  return res.data;
};

//리스트
export const getList = async (pageParam) => {
  const { page, size } = pageParam;

  const res = await jwtAxios.get(`${prefix}/list`, { params: { page, size } });

  return res.data;
};

//등록
export const postAdd = async (todoObj) => {
  const res = await jwtAxios.post(`${prefix}/`, todoObj);

  return res.data;
};

//삭제
export const deleteOne = async (todoObj) => {
  const res = await jwtAxios.delete(`${prefix}/`, todoObj);

  return res.data;
};

//수정
export const putOne = async (todoObj) => {
  const res = await jwtAxios.put(`${prefix}/${todoObj.id}`, todoObj);

  return res.data;
};
