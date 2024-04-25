import axios from "axios";
import { API_SERVER_HOST } from "./memberApi";

const rest_api_key = "5c265d6a0e5249c9cbaccb349602bced";
const redirect_uri = "http://localhost:5173/member/kakao";

const auth_code_path = "https://kauth.kakao.com/oauth/authorize";

const access_token_url = `https://kauth.kakao.com/oauth/token`; //추가

//인가 코드 받기위한 url
export const getKakaoLoginLink = () => {
  const kakaoUrl = `${auth_code_path}?client_id=${rest_api_key}&redirect_uri=${redirect_uri}&response_type=code`;

  return kakaoUrl;
};

//엑세스 토큰 받기, 인가코드는 매번 변경되므로 파마리터로 처리
export const getAccessToken = async (authCode) => {
  const header = {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
    },
  };
  const params = {
    grant_type: "authorization_code",
    client_id: rest_api_key,
    redirect_uri: redirect_uri,
    code: authCode,
    client_secret: "qxNkWg6rGCojfGovZsqf5vnZZkcNn8Rj",
  };

  const res = await axios.post(access_token_url, params, header);

  const accessToken = res.data.access_token;

  return accessToken;
};

export const getMemberWithAccessToken = async (accessToken) => {
  const res = await axios.get(
    `${API_SERVER_HOST}/api/member/kakao?accessToken=${accessToken}`
  );
  return res.data;
};
