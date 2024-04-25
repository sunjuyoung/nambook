import { Link } from "react-router-dom";
import { getKakaoLoginLink } from "../../api/kakaoApi";

const KakaoLoginComponent = () => {
  const link = getKakaoLoginLink();

  return (
    <div className="flex flex-col">
      <div className="text-center text-blue-500">
        로그인시에 자동 가입처리 됩니다
      </div>
      <div className="flex justify-center w-full">
        <div className="w-3/4 p-2 m-6 text-3xl font-extrabold text-center text-white bg-yellow-500 rounded shadow-sm">
          <Link to={link}>KAKAO LOGIN</Link>
        </div>
      </div>
    </div>
  );
};

export default KakaoLoginComponent;
