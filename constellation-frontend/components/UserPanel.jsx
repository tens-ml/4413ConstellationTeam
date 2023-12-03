import { signOut } from "next-auth/react";
import Button from "./Button";
import Paper from "./Paper";

const UserPanel = ({ user = {}, className }) => {
  return (
    <Paper
      width="400px"
      className={`flex flex-col justify-center ${className}`}
    >
      <div className="flex items-center justify-center space-x-4">
        <img
          className=""
          src={user.image}
          alt="[Avatar]"
          width={45}
          height={45}
        />
        <h1
          className="text-lg text-semibold text-ellipsis overflow-hidden whitespace-nowrap"
          style={{ maxWidth: "200px" }}
        >
          {user.email}
        </h1>
      </div>
      <Button className="mt-8" onClick={() => signOut({ callbackUrl: "/" })}>
        Logout
      </Button>
    </Paper>
  );
};

export default UserPanel;
