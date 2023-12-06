import Button from "@/components/Button";
import Input from "@/components/Input";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import { useRouter } from "next/router";
import { useState } from "react";
const Signup = () => {
  const router = useRouter();
  const [form, setForm] = useState({
    username: "",
    password: "",
    firstName: "",
    lastName: "",
    streetAddress: "",
    city: "",
    postalCode: "",
    country: "",
    province: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(`${process.env.GATEWAY_URL}/users`);
    fetch(`${process.env.GATEWAY_URL}/users`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error();
        }

        alert("Account created successfully");
        router.push("/");
      })
      .catch((error) => {
        alert("Failed to create account, user already exists");
      });
  };

  return (
    <Shell>
      <Paper width="600px" className="mt-12 flex flex-col">
        <p className="text-center font-semibold mt-2 font-sans text-3xl text-gray-800">
          Sign up
        </p>
        <form onSubmit={handleSubmit}>
          <div className="flex space-x-4">
            <div>
              <Input
                className="mt-12"
                placeholder="Username"
                name="username"
                value={form.username}
                onChange={handleChange}
                required
              />
              <Input
                className="mt-12"
                placeholder="Password"
                name="password"
                value={form.password}
                onChange={handleChange}
                required
              />
              <Input
                className="mt-12"
                placeholder="First Name"
                name="firstName"
                value={form.firstName}
                onChange={handleChange}
                required
              />
              <Input
                className="mt-12"
                placeholder="Last Name"
                name="lastName"
                value={form.lastName}
                onChange={handleChange}
                required
              />
            </div>
            <div>
              <Input
                className="mt-12"
                placeholder="Street Address"
                name="streetAddress"
                value={form.streetAddress}
                onChange={handleChange}
                required
              />
              <Input
                className="mt-12"
                placeholder="Postal Code"
                name="postalCode"
                value={form.postalCode}
                onChange={handleChange}
                required
              />
              <Input
                className="mt-12"
                placeholder="City"
                name="city"
                value={form.city}
                onChange={handleChange}
                required
              />
              <Input
                className="mt-12"
                placeholder="Country"
                name="country"
                value={form.country}
                onChange={handleChange}
                required
              />
              <Input
                className="mt-12"
                placeholder="Province"
                name="province"
                value={form.province}
                onChange={handleChange}
                required
              />
            </div>
          </div>

          <Button type="submit" className="mt-12 w-full">
            Create Account
          </Button>
        </form>

        <Button className="mt-1" onClick={() => router.push("/")}>
          Back
        </Button>
      </Paper>
    </Shell>
  );
};

export default Signup;
