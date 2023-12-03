const fetchWithAuth = async (url, token) => {
  opts = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  return () => fetch(url, opts);
};

export default fetchWithAuth;
