import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import GithubProvider from "next-auth/providers/github";

const credentialProvider = CredentialsProvider({
  // The name to display on the sign in form (e.g. 'Sign in with...')
  name: "Credentials",
  credentials: {
    username: { label: "Username", type: "text" },
    password: { label: "Password", type: "password" },
  },

  async authorize(credentials, req) {
    // const res = await fetch(`${process.env.GATEWAY_URL}/users/login`, {
    //   method: "POST",
    //   body: JSON.stringify(credentials),
    //   headers: { "Content-Type": "application/json" },
    // });
    let res = {
      ok: true,
    };
    if (res.ok) {
      console.log("resok");
      return {
        name: "danny",
        email: "danny@dan.com",
        image: "https://www.jea.com/cdn/images/avatar/avatar-alt.svg",
      };
    }
    console.log("didnt get okay");
    return null;
  },
});

export const authOptions = {
  // Configure one or more authentication providers
  providers: [
    GithubProvider({
      clientId: process.env.GITHUB_ID,
      clientSecret: process.env.GITHUB_SECRET,
    }),
    credentialProvider,
  ],
};

export default NextAuth(authOptions);
