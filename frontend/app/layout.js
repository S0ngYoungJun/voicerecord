import localFont from "next/font/local";
import "./globals.css";

const geistSans = localFont({
  src: "./fonts/GeistVF.woff",
  variable: "--font-geist-sans",
  weight: "100 900",
});
const geistMono = localFont({
  src: "./fonts/GeistMonoVF.woff",
  variable: "--font-geist-mono",
  weight: "100 900",
});

export const metadata = {
  title: "Voice Note Application",
  description: "Next.js와 Spring Boot를 이용한 노트 관리 앱",
};

export default function RootLayout({ children }) {
  return (
      <html lang="ko">
          <body>
              <header>
                  <h1>Voice Note App</h1>
              </header>
              <main>{children}</main>
              <footer>
                  <p>© 2024 Voice Note Application</p>
              </footer>
          </body>
      </html>
  );
}
