import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import useCustomLogin from "../../hooks/useCustomLogin";
import { modifyMember } from "../../api/memberApi";
import ResultModal from "../common/ResultModal";

const initState = {
  email: "",
  pw: "",
  nickname: "",
};

const ModifyComponent = () => {
  const [member, setMember] = useState(initState);
  const loginInfo = useSelector((state) => state.loginSlice);

  const { moveToLogin } = useCustomLogin();

  const [result, setResult] = useState();

  useEffect(() => {
    setMember({ ...loginInfo, pw: "ABCD" });
  }, [loginInfo]);

  const handleChange = (e) => {
    member[e.target.name] = e.target.value;

    setMember({ ...member });
  };

  const handleClickModify = () => {
    modifyMember(member).then((result) => {
      setResult("Moodified");
    });
  };

  const colseModal = () => {
    setResult(null);
    moveToLogin();
  };

  return (
    <div className="mt-6">
      {result ? (
        <ResultModal
          title={"회원정보"}
          content={"정보수정완료"}
          callbackFn={colseModal}
        ></ResultModal>
      ) : (
        <></>
      )}

      <div className="flex justify-center">
        <div className="relative flex flex-wrap items-stretch w-full mb-4">
          <div className="w-1/5 p-6 font-bold text-right">Email</div>
          <input
            className="w-4/5 p-6 border border-solid rounded-r shadow-md border-neutral-300"
            name="email"
            type={"text"}
            value={member.email}
            readOnly
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative flex flex-wrap items-stretch w-full mb-4">
          <div className="w-1/5 p-6 font-bold text-right">Password</div>
          <input
            className="w-4/5 p-6 border border-solid rounded-r shadow-md border-neutral-300"
            name="pw"
            type={"password"}
            value={member.pw}
            onChange={handleChange}
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative flex flex-wrap items-stretch w-full mb-4">
          <div className="w-1/5 p-6 font-bold text-right">Nickname</div>
          <input
            className="w-4/5 p-6 border border-solid rounded-r shadow-md border-neutral-300"
            name="nickname"
            type={"text"}
            value={member.nickname}
            onChange={handleChange}
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative flex flex-wrap justify-end w-full mb-4">
          <button
            type="button"
            className="w-32 p-4 m-2 text-xl text-white bg-blue-500 rounded"
            onClick={handleClickModify}
          >
            Modify
          </button>
        </div>
      </div>
    </div>
  );
};

export default ModifyComponent;
