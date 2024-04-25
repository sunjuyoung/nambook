import { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { getAccessToken, getMemberWithAccessToken } from "../../api/kakaoApi";

//인가 코드의 페이지 처리
const KakaoRedirectPage = () => {
  const [searchParams] = useSearchParams();
  const authCode = searchParams.get("code");

  //인가코드가 변경되었을때 accessToken호출
  useEffect(() => {
    getAccessToken(authCode).then((accessToken) => {
      console.log(accessToken);
      getMemberWithAccessToken(accessToken).then((result) => {
        console.log("-------");
        console.log(result);
      });
    });
  }, [authCode]);

  return (
    <div>
      <div>Login redirect</div>
      <div>{authCode}</div>
    </div>
  );
};

export default KakaoRedirectPage;
