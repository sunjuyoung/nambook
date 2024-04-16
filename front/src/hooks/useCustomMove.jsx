import { useState } from "react";
import {
  createSearchParams,
  useNavigate,
  useSearchParams,
} from "react-router-dom";

const getNum = (param, defaultValue) => {
  if (!param) {
    return defaultValue;
  }

  return parseInt(param);
};

//외부에서 페이지이동을 위한 함수를 제공
const useCustomMove = () => {
  const navigate = useNavigate();
  const [queryParams] = useSearchParams();

  const [refresh, setRefresh] = useState(false);

  const page = getNum(queryParams.get("page"), 1);
  const size = getNum(queryParams.get("size"), 10);

  const queryDefault = createSearchParams({ page, size }).toString();

  //목록화면 이동
  const moveToList = (pageParam) => {
    let queryStr = "";
    if (pageParam) {
      const pageNum = getNum(pageParam.page, 1);
      const sizeNum = getNum(pageParam.size, 10);

      queryStr = createSearchParams({
        page: pageNum,
        size: sizeNum,
      }).toString();
    } else {
      queryStr = queryDefault;
    }

    navigate({
      pathname: `../list`,
      search: queryStr,
    });
    setRefresh(!refresh); //추가
  };

  //수정화면 이동
  const moveToModify = (tno) => {
    navigate({
      pathname: `../modify/${tno}`,
      search: queryDefault,
    });
  };

  //수정화면 이동
  const moveToRead = (tno) => {
    navigate({
      pathname: `../read/${tno}`,
      search: queryDefault,
    });
  };

  return { moveToList, page, size, moveToModify, refresh, moveToRead };
};

export default useCustomMove;
