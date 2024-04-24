import { useDispatch, useSelector } from "react-redux";
import { createSearchParams, Navigate, useNavigate } from "react-router-dom";
import { loginAsync, logout } from "../slices/loginSlice";

const useCustomLogin = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const loginState = useSelector((state) => state.loginSlice);
  const isLogin = loginState.email ? true : false;

  const exceptionHandler = (ex) => {
    console.log("ex------");
    console.log(ex);
    const errorMsg = ex.response.data.error;
    const errorStr = createSearchParams({ error: errorMsg }).toString();
    if (errorMsg === "REQUIRE_LOGIN") {
      //axios 인터셉터에서 쿠키확인 후 없으면 해당 에러 발생
      alert("로그인 해야만 합니다");
      navigate(
        { pathname: "/member/login", search: errorStr },
        { replace: true }
      );
      return;
    }
    if (errorMsg === "ERROR_ACCESSDENIED") {
      alert("해당 메뉴를 사용할 수 있는 권한이 없습니다.");
      navigate({ pathname: "/member/login", search: errorStr });
    }
  };

  //------------------로그인
  const doLogin = async (loginParam) => {
    const action = await dispatch(loginAsync(loginParam));
    return action.payload;
  };

  //-------------------로그아웃
  const doLogout = () => {
    dispatch(logout());
  };

  //----------------페이지 이동
  const moveToPath = (path) => {
    navigate({ pathname: path }, { replace: true }); //뒤로 가기 했을때 로그인 화면을 볼 수 없게
  };

  //----------------------로그인 페이지로 이동
  const moveToLogin = () => {
    navigate({ pathname: "/member/login" }, { replace: true });
  };

  //----------------------로그인 페이지로 이동 컴포넌트
  const moveToLoginReturn = () => {
    return <Navigate replace to="/member/login" />;
  };

  return {
    doLogin,
    doLogout,
    moveToPath,
    moveToLogin,
    moveToLoginReturn,
    isLogin,
    exceptionHandler,
  };
};

export default useCustomLogin;
