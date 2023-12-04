import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import GithubProvider from "next-auth/providers/github";

const credentialProvider = CredentialsProvider({
  name: "Credentials",
  credentials: {
    username: { label: "Username", type: "text" },
    password: { label: "Password", type: "password" },
  },

  async authorize(credentials, req) {
    const res = await fetch(`${process.env.GATEWAY_URL}/users/login`, {
      method: "POST",
      body: JSON.stringify(credentials),
      headers: { "Content-Type": "application/json" },
    });

    if (res.ok) {
      const data = await res.json();
      console.log(data);
      return {
        id: data.id,
        name: data.username,
        email: data.username + "@something.com",
        image: "https://www.jea.com/cdn/images/avatar/avatar-alt.svg",
      };
    }
    return null; //a
  },
});

export const authOptions = {
  providers: [
    GithubProvider({
      clientId: process.env.GITHUB_ID,
      clientSecret: process.env.GITHUB_SECRET,
    }),
    credentialProvider,
  ],
  callbacks: {
    session: async ({ session, token }) => {
      if (session?.user) {
        session.user.id = parseInt(token.uid);
      }
      return session;
    },
    jwt: async ({ user, token }) => {
      if (user) {
        token.uid = user.id;
      }
      return token;
    },
  },
  session: {
    strategy: "jwt",
  },
};

export default NextAuth(authOptions);
