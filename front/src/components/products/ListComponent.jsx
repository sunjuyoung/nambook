import { useEffect, useState } from "react";
import useCustomMove from "../../hooks/useCustomMove";
import { API_SERVER_HOST, getList } from "../../api/productsApi";
import FetchingModal from "../common/FetchingModal";
import PageComponent from "../common/PageComponent";
import useCustomLogin from "../../hooks/useCustomLogin";

const host = API_SERVER_HOST;

const initState = {
  dtoList: [],
  pageNumList: [],
  pageRequestDTO: null,
  prev: false,
  next: false,
  totoalCount: 0,
  prevPage: 0,
  nextPage: 0,
  totalPage: 0,
  current: 0,
};

const ListComponent = () => {
  const { page, size, refresh, moveToList, moveToRead } = useCustomMove();

  const { exceptionHandler } = useCustomLogin();

  //serverData는 나중에 사용
  const [serverData, setServerData] = useState(initState);

  //for FetchingModal
  const [fetching, setFetching] = useState(false);

  useEffect(() => {
    setFetching(true);

    getList({ page, size })
      .then((data) => {
        console.log(data);
        setServerData(data);
        setFetching(false);
      })
      .catch((e) => {
        exceptionHandler(e);
      });
  }, [page, size, refresh]);

  return (
    <div className="mt-10 ml-2 mr-2 border-2 border-blue-100">
      {fetching ? <FetchingModal /> : <></>}

      <div className="flex flex-wrap p-6 mx-auto">
        {serverData.dtoList?.map((product) => (
          <div
            key={product.id}
            className="w-1/2 p-1 border-2 rounded shadow-md"
            onClick={() => moveToRead(product.id)}
          >
            <div className="flex flex-col h-full">
              <div className="w-full p-2 text-2xl font-extrabold ">
                {product.id}
              </div>
              <div className="flex flex-col w-full p-2 m-1 text-1xl">
                <div className="w-full overflow-hidden ">
                  <img
                    alt="product"
                    className="m-auto rounded-md w-60"
                    src={`${host}/api/products/view/s_${product.uploadFileNames[0]}`}
                  />
                </div>

                <div className="bottom-0 font-extrabold bg-white">
                  <div className="p-1 text-center">이름: {product.pname}</div>
                  <div className="p-1 text-center">가격: {product.price}</div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      <PageComponent
        serverData={serverData}
        movePage={moveToList}
      ></PageComponent>
    </div>
  );
};
export default ListComponent;
