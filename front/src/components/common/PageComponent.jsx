const PageComponent = ({ serverData, movePage }) => {
  return (
    <div className="flex justify-center m-6">
      {serverData.prev ? (
        <div
          className="w-16 p-2 m-2 font-bold text-center text-blue-400 "
          onClick={() => movePage({ page: serverData.prevPage })}
        >
          Prev
        </div>
      ) : (
        <></>
      )}

      {serverData.pageNumList.map((pageNum) => (
        <div
          key={pageNum}
          className={`m-2 p-2 w-12  text-center rounded shadow-md text-white ${
            serverData.current === pageNum ? "bg-gray-500" : "bg-blue-400"
          }`}
          onClick={() => movePage({ page: pageNum })}
        >
          {pageNum}
        </div>
      ))}

      {serverData.next ? (
        <div
          className="w-16 p-2 m-2 font-bold text-center text-blue-400"
          onClick={() => movePage({ page: serverData.nextPage })}
        >
          Next
        </div>
      ) : (
        <></>
      )}
    </div>
  );
};

export default PageComponent;
