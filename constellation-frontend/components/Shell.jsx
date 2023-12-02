import { Inter } from "next/font/google";
import Header from "./Header";

const inter = Inter({ subsets: ["latin"] });

const Shell = ({ children }) => {
  return (
    <main
      className={`flex min-h-screen flex-col items-center ${inter.className} bg-gray-400 text-black select-none`}
    >
      <Header />
      {children}
    </main>
  );
};

export default Shell;
