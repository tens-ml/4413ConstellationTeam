const Paper = ({ children, className = "", width }) => {
  return (
    <div
      className={`shadow-md rounded-md flex p-8 bg-white ${className}`}
      style={{ width }}
    >
      {children}
    </div>
  );
};

export default Paper;
