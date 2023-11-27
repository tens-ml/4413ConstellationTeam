const Title = ({ children, className }) => {
  return (
    <h1
      className={`font-semibold font-sans text-3xl text-gray-800 ${className}`}
    >
      {children}
    </h1>
  );
};

export default Title;
