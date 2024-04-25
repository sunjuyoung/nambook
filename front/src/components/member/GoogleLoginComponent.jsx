import { GoogleLogin, GoogleOAuthProvider } from "@react-oauth/google";
import { goolgLogin } from "../../api/memberApi";

const GoogleLoginComponent = () => {
  const clientId =
    "905470897989-bm31mt1c8v6n3323fmoqa5gt1du5l4p8.apps.googleusercontent.com";

  return (
    <>
      <GoogleOAuthProvider clientId={clientId}>
        <GoogleLogin
          onSuccess={(res) => {
            console.log(res.credential);
            goolgLogin(res.credential).then((result) => {
              console.log(result);
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
