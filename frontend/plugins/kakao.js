

export default ({ app, req, res, redirect, route }, inject) => {

  // kakao common script
  const kakao = {};

  kakao.cookie = (function() {
    return {
      set(name, value, time) {
        const date = new Date(time);
        const cookie = `${name}=${value};expires=${date.toUTCString()};path=/`;
        if (res) {
          res.setHeader('Set-Cookie', cookie);
        } else {
          document.cookie = cookie;
        }
      },
      get(name) {
        const key = `${name}=`;
        const cookies = (req?.headers || document).cookie?.split(';');
        for (let i = 0, n = cookies?.length; i < n; ++i) {
          let cookie = cookies[i];
          while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
          }
          if (cookie.indexOf(key) === 0) {
            return cookie.substring(key.length);
          }
        }
        return null;
      },
      delete(name) {
        this.set(name, '', new Date().getTime() - 1);
      },
    }
  })();

  kakao.fetch = (function() {
    const defaultOptions = {
      headers: {
        'Content-Type': 'application/json'
      }
    };
    const excludeErrorCode = [401];
    const errorHandlerObject = {
      //400(responseJson, { fieldErrorMessage }) {
        // bad request
        //responseJson?.errors?.forEach(({ field, message }) => fieldErrorMessage[field] = message)
      //},
      401() {
        if (process.client) {
          alert('세션이 만료되어 로그인 페이지로 이동합니다.');
        }
        kakao.cookie.delete('access_token');
        redirect('/login');
      },
      default({code}) {
        redirect(`/error?code=${code}`);
      }
    };
    async function request(url, {isErrorHandler, ...restOptions}) {
      // TODO : Deep copy
      const mergeOptions = {
        ...defaultOptions,
        ...restOptions,
      };
      const accessToken = kakao.cookie.get('access_token');
      if (accessToken) {
        mergeOptions.headers['Authorization'] = `Bearer ${accessToken}`;
      }
      const response = await fetch(`${process.env.API_URL}${url}`, mergeOptions);
      const responseJson = await response.json();
      if (!response.ok) {
        const { code } = responseJson;
        if (isErrorHandler && !excludeErrorCode.includes(code)) {
          return Promise.reject(responseJson);
        }
        (errorHandlerObject[code] || errorHandlerObject.default)(responseJson);
        return Promise.reject();
      }
      return responseJson;
    }
    return {
      get(url, options) {
        return request(url, {
          ...options,
          method: 'GET'
        });
      },
      post(url, data, options) {
        return request(url, {
          ...options,
          method: 'POST',
          body: JSON.stringify(data)
        });
      },
      patch(url, data, options) {
        return request(url, {
          ...options,
          method: 'PATCH',
          ...(data ? {body: JSON.stringify(data)} : {})
        });
      },
      // TODO : put, delete
    }
  })();

  inject('kakao', kakao);
}
