import { useRouter } from "next/router";
import { useState } from "react";

const useSearch = () => {
  const router = useRouter();
  const [filter, setFilter] = useState("");

  const handleSearch = () => {
    router.replace(
      {
        pathname: "/catalog",
        query: filter ? { filter } : {},
      },
      undefined,
      { shallow: true }
    );
  };

  return { filter, setFilter, handleSearch };
};

export default useSearch;
