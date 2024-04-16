import { useEffect, useState } from "react";
import { getOne } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";

const initState = {
  tno: 0,
  title: "",
  writer: "",
  dueDate: null,
  complete: false,
};

const ReadComponent = ({ tno }) => {
  const [todo, setTodo] = useState(initState);

  const { moveToList, moveToModify } = useCustomMove();

  useEffect(() => {
    getOne(tno).then((data) => {
      setTodo(data);
    });
  }, [tno]);

  return (
    <div className="p-4 m-2 mt-10 border-2 border-sky-200 ">
      {makeDiv("Tno", todo.tno)}
      {makeDiv("writer", todo.writer)}
      {makeDiv("title", todo.title)}
      {makeDiv("dueDate", todo.dueDate)}
      {makeDiv("complete", todo.complete ? "완료" : "미완료")}

      <div className="flex justify-end p-4">
        <button
          className="p-2 m-2 text-white bg-blue-500 rounded"
          onClick={() => moveToList()}
        >
          목록
        </button>
        <button
          type="button"
          className="w-32 p-4 m-2 text-xl text-white bg-red-500 rounded"
          onClick={() => moveToModify(tno)}
        >
          수정
        </button>
      </div>
    </div>
  );
};

const makeDiv = (title, value) => (
  <div className="flex justify-center">
    <div className="relative flex flex-wrap items-stretch w-full mb-4">
      <div className="w-1/5 p-6 font-bold text-right">{title}</div>
      <div className="w-4/5 p-6 border border-solid rounded-r shadow-md">
        {value}
      </div>
    </div>
  </div>
);

export default ReadComponent;
