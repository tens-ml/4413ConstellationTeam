const Input = ({
  type,
  placeholder,
  value,
  onChange,
  className,
  label,
  required = false,
  ...rest
}) => {
  if (!label) {
    return (
      <input
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        required={required}
        className={`mt-1 w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-400 focus:border-transparent ${className}`}
        {...rest}
      />
    );
  } else {
    return (
      <div className={`flex flex-col ${className}`}>
        <label className="text-gray-700">{label}</label>
        <input
          type={type}
          placeholder={placeholder}
          value={value}
          onChange={onChange}
          className={`mt-1 w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-400 focus:border-transparent`}
          required={required}
        />
      </div>
    );
  }
};

export default Input;
