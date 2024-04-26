import BasicLayout from "../../layouts/BasicLayout";
import ModifyComponent from "../../components/member/ModifyComponent";
const ModifyPage = () => {
  return (
    <BasicLayout>
      <div className="text-3xl ">Member Modify Page</div>

      <div className="w-full p-2 mt-4 bg-white">
        <ModifyComponent></ModifyComponent>
      </div>
    </BasicLayout>
  );
};

export default ModifyPage;
