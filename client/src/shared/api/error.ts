export class ApiError extends Error {
  public readonly status: number;
  public readonly message: string;
  public readonly code?: number;
  public readonly details?: unknown;

  constructor(params: {
    status: number;
    message: string;
    code?: number;
    details?: unknown;
  }) {
    super(params.message);
    this.status = params.status;
    this.message = params.message;
    this.code = params.code;
    this.details = params.details;
    this.name = "APiError";
  }
}
