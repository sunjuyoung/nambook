import { parse } from "postcss";
import { createSearchParams, useSearchParams } from "react-router-dom";
import ListComponent from "../../components/todo/ListComponent";

const ListPage = () => {
  const [queryParams] = useSearchParams();
  const page = queryParams.get("page") ? parseInt(queryParams.get("page")) : 1;
  const size = queryParams.get("size") ? parseInt(queryParams.get("size")) : 10;

  return (
    <div className="w-full p-4 bg-white">
      <div className="text-3xl font-extralight">
        Todo List page Component {page} , {size}
      </div>
      <ListComponent />
    </div>
  );
};

export default ListPage;
