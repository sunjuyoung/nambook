import { Link } from "react-router-dom";
import BasicLayout from "../layouts/BasicLayout";
//a태그는 페이지를 새로고침하므로 Link를 사용한다.
//link는 리액트 내부에서 해당 컴포넌트만을 처리한다

const MainPage = () => {
  return (
    <BasicLayout>
      <div>
        <Link to="/about">About</Link>
      </div>
      <div className="text-3xl">MainPage</div>
    </BasicLayout>
  );
};

export default MainPage;
