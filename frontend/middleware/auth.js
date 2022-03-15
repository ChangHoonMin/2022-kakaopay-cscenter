
export default function ({ $kakao, redirect }) {
  const accessToken = $kakao.cookie.get('access_token');
  if (!accessToken) {
    redirect('/login');
  }
}
