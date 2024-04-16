import { useParams } from "react-router-dom";
import ModifyComponent from "../../components/todo/ModifyComponent";

const ModifyPage = () => {
  const { tno } = useParams();
  return (
    <div className="w-full p-4 bg-white">
      <div className="text-3xl font-extrabold">Todo Modify Page</div>

      <ModifyComponent tno={tno} />
    </div>
  );
};

export default ModifyPage;
