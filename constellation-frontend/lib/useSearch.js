import { useRouter } from "next/router";
import { useEffect, useState } from "react";

const useSearch = () => {
  const router = useRouter();
  const [filter, setFilter] = useState("");

  const handleSearch = () => {
    router.push({ to: "/catalog", query: { filter } });
  };

  useEffect(() => {
    setFilter(router.query.filter || "");
  }, [router.query.filter]);

  return { filter, setFilter, handleSearch };
};

export default useSearch;
