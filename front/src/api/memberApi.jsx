import axios from "axios";

export const API_SERVER_HOST = "http://localhost:8080";

const host = `${API_SERVER_HOST}/api/member`;

export const loginMember = async (loginParam) => {
  const header = { headers: { "Content-Type": "www-form-urlencoded" } };

  const form = new FormData();
  form.append("username", loginParam.email);
  form.append("password", loginParam.pw);

  const res = await axios.post(`${host}/login`, form, header);
  return res.data;
};
