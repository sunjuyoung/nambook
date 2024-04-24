import axios from "axios";
import { getCookie, setCookie } from "./cookieUtil";
import { API_SERVER_HOST } from "../api/todoApi";

//인터셉터 기능을 추가
//axios 요청이나 응답시에 추가적인 작업을 수행할수있는데 이를 통해
//쿠키로 보관된 토큰을 처리하는 작업이나 자동으로 refresh토큰을 사용하는 처리를 할 수 있다.
//jwt사용하는 toapi, productapi 에서는 기존 axios 대신 jwtAxios를 사용한다

const jwtAxios = axios.create();

const refreshJWT = async (accessToken, refreshToken) => {
  const host = API_SERVER_HOST;

  const header = { headers: { Authorization: `Bearer ${accessToken}` } };

  //post방식으로 하는게 더 좋음
  const res = await axios.get(
    `${host}/api/member/refresh?refreshToken=${refreshToken}`,
    header
  );
  console.log("---------refreshJWT-------------");
  return res.data;
};

//요청 전 AUTHORIZATION 헤더 지정
const beforeReq = (config) => {
  console.log("beforeReq");
  //api호출전 authorization 헤더추가
  //쿠키가 없다면 무조건 예외 발생
  const memberInfo = getCookie("member");
  if (!memberInfo) {
    console.log("memeberCookie not found");
    return Promise.reject({
      response: {
        data: {
          error: "REQUIRE_LOGIN",
        },
      },
    });
  }
  const { accessToken } = memberInfo;
  config.headers.Authorization = `Bearer ${accessToken}`;

  return config;
};

//fail request
const requestFail = (error) => {
  console.log("requestFail");
  return Promise.reject(error);
};

//토큰만료 응답을 받았다면 , refreshToken 재요청(사일런트 리프레쉬)
const beforeRes = async (response) => {
  console.log(".......beforeRes");

  //console.log(response);
  //JWT 기간 만료시  refreshToken으로 갱신
  const data = response.data;
  if (data && data.error === "ERROR_ACCESS_TOKEN") {
    const memberCookie = getCookie("member");
    const result = await refreshJWT(
      memberCookie.accessToken,
      memberCookie.refreshToken
    );
    // console.log("refreshJWT result: " + result.accessToken);
    memberCookie.accessToken = result.accessToken;
    memberCookie.refreshToken = result.refreshToken;

    //갱신된 토큰 저장
    setCookie("member", JSON.stringify(memberCookie), 1);

    //원래 호출하려던 api 다시 시도
    const originReq = response.config;
    originReq.headers.Authorization = `Bearer ${result.accessToken}`;
    return await axios(originReq);
  }

  return response;
};

const responseFail = (error) => {
  console.log("responseFail");
  return Promise.reject(error);
};

jwtAxios.interceptors.request.use(beforeReq, requestFail);
jwtAxios.interceptors.response.use(beforeRes, responseFail);

export default jwtAxios;
