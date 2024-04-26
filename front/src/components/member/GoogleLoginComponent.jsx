import { GoogleLogin, GoogleOAuthProvider } from "@react-oauth/google";
import { goolgLogin } from "../../api/memberApi";
import { useDispatch } from "react-redux";
import { login } from "../../slices/loginSlice";
import useCustomLogin from "../../hooks/useCustomLogin";

const GoogleLoginComponent = () => {
  const clientId =
    "905470897989-bm31mt1c8v6n3323fmoqa5gt1du5l4p8.apps.googleusercontent.com";
  const dispatch = useDispatch();
  const { moveToPath } = useCustomLogin();

  return (
    <>
      <GoogleOAuthProvider clientId={clientId}>
        <GoogleLogin
          onSuccess={(res) => {
            console.log(res.credential);
            goolgLogin(res.credential).then((result) => {
              console.log(result);
              dispatch(login(result));
              //소셜
              if (result && !result.social) {
                moveToPath("/");
              } else {
                alert("비밀번호를 변경하세요");
                moveToPath("/member/modify");
              }
            });
          }}
          onFailure={(err) => {
            console.log(err);
          }}
        />
      </GoogleOAuthProvider>
    </>
  );
};

export default GoogleLoginComponent;
