const STORAGE_KEY = "accessToken";
let _accessToken: string | null = localStorage.getItem(STORAGE_KEY);

export const token = {
  get() {
    return _accessToken;
  },
  set(v: string | null) {
    _accessToken = v;
    if (v) {
      localStorage.setItem(STORAGE_KEY, v);
    } else {
      localStorage.removeItem(STORAGE_KEY);
    }
  },
  has() {
    return _accessToken;
  },
  clear() {
    _accessToken = null;
    localStorage.removeItem(STORAGE_KEY);
  },
};
