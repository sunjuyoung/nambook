import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { loginMember } from "../api/memberApi";
import { setCookie, getCookie, removeCookie } from "../util/cookieUtil";

//애플리케이션 초기활 될때 loginSlice도 초기화되므로 member쿠키가 있는 상태에서 새로고침을해도 로그인 상태가 유지된다

const initstate = {
  email: "",
};
const loadMemberCookie = () => {
  const memberInfo = getCookie("member");
  if (memberInfo && memberInfo.nickname) {
    //한글 처리를 안심하고 사용하기위해 현재는 빼도 상관없다.
    memberInfo.nickname = decodeURIComponent(memberInfo.nickname);
  }
  return memberInfo;
};

//createAsyncThunk 를사용해서 비동기 통신을 호출하는 함수를 작성하고
//비동기 호출의 상태에 따라 동작하는 extraReducer

export const loginAsync = createAsyncThunk("loginAsync", (param) => {
  console.log("loginAsync");
  return loginMember(param);
});

const loginSlice = createSlice({
  name: "LoginSlice",
  initialState: loadMemberCookie() || initstate, //쿠키가 없다면 초깃값 사용
  reducers: {
    login: (state, action) => {
      console.log("login..");
      const data = action.payload;

      return { email: data.email };
    },
    logout: (state, action) => {
      console.log("logout..");
      removeCookie("member");

      return { ...initstate };
    },
  },

  extraReducers: (builder) => {
    builder
      //완료
      .addCase(loginAsync.fulfilled, (state, action) => {
        console.log("fulfilled");
        const payload = action.payload;
        if (!payload.error) {
          console.log("cookieset");
          setCookie("member", JSON.stringify(payload), 1); //1일
        }
        return payload;
      })
      //처리중
      .addCase(loginAsync.pending, (state, action) => {
        console.log("pending");
      })
      //에러
      .addCase(loginAsync.rejected, (state, action) => {
        console.log("rejected");
      });
  },
});

export const { login, logout } = loginSlice.actions;

export default loginSlice.reducer;
