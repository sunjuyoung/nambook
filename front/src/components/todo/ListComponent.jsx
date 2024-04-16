import { useEffect, useState } from "react";
import useCustomMove from "../../hooks/useCustomMove";
import { getList } from "../../api/todoApi";
import PageComponent from "../common/PageComponent";

const initState = {
  dtoList: [],
  pageNumList: [],
  pageRequestDto: null,
  prev: false,

  next: false,
  totalCount: 0,
  prevPage: 0,
  nextPage: 0,
  totalPage: 0,
  current: 0,
};

const ListComponent = () => {
  const { page, size, moveToRead, moveToList, refresh } = useCustomMove();

  const [serverData, setServerData] = useState(initState);

  useEffect(() => {
    getList({ page, size }).then((data) => {
      console.log(data);
      setServerData(data);
    });
  }, [page, size, refresh]);

  return (
    <div className="mt-10 ml-2 mr-2 border-2 border-blue-100">
      <div className="flex flex-wrap justify-center p-6 mx-auto">
        {serverData.dtoList.map((todo) => (
          <div
            key={todo.id}
            className="w-full min-w-[400px]  p-2 m-2 rounded shadow-md"
            onClick={() => moveToRead(todo.id)} //이벤트 처리 추가
          >
            <div className="flex ">
              <div className="w-1/12 p-2 text-2xl font-extrabold">
                {todo.id}
              </div>
              <div className="w-8/12 p-2 m-1 font-extrabold text-1xl">
                {todo.title}
              </div>
              <div className="p-2 m-1 font-medium text-1xl w-2/10">
                {todo.dueDate}
              </div>
            </div>
          </div>
        ))}
      </div>
      <PageComponent serverData={serverData} movePage={moveToList} />
    </div>
  );
};

export default ListComponent;
