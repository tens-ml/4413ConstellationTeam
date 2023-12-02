const Button = ({ children, className, ...rest }) => {
  return (
    <button
      className={`bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded ${className}`}
      {...rest}
    >
      {children}
    </button>
  );
};

export default Button;
