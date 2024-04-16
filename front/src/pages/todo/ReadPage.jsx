import { useParams } from "react-router-dom";

import ReadComponent from "../../components/todo/ReadComponent";

const ReadPage = () => {
  const { tno } = useParams();

  return (
    <div className="text-3xl font-extrabold">
      Todo Read Page Component {tno}
      <div>
        <ReadComponent tno={tno}></ReadComponent>
      </div>
    </div>
  );
};

export default ReadPage;
