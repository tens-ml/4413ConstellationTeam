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
      return {
        id: data.id,
        firstName: data.firstName,
        lastName: data.lastName,
        streetAddress: data.streetAddress,
        postalCode: data.postalCode,
        city: data.city,
        province: data.province,
        country: data.country,
        email: data.username + "@something.com",
        image: "https://www.jea.com/cdn/images/avatar/avatar-alt.svg",
        test: 1,
      };
    }
    return null;
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
  session: {
    jwt: true,
  },
  callbacks: {
    session: async ({ session, token }) => {
      if (session?.user) {
        session.user.id = parseInt(token.uid);
        session.user.firstName = token.firstName;
        session.user.lastName = token.lastName;
        session.user.streetAddress = token.streetAddress;
        session.user.postalCode = token.postalCode;
        session.user.city = token.city;
        session.user.province = token.province;
        session.user.country = token.country;
      }
      return session;
    },
    jwt: async ({ user, token }) => {
      if (user) {
        token.uid = user.id;
        token.firstName = user.firstName;
        token.lastName = user.lastName;
        token.streetAddress = user.streetAddress;
        token.postalCode = user.postalCode;
        token.city = user.city;
        token.province = user.province;
        token.country = user.country;
      }
      return token;
    },
  },
};

export default NextAuth(authOptions);
