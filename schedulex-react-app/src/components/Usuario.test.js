import React from "react";
import { render, screen, act } from "@testing-library/react";
import Usuario from "./Usuario";

beforeEach(() => {
  jest.spyOn(global, "fetch").mockResolvedValue({
    json: jest.fn().mockResolvedValue([{
        id: 1,
        activity: "1",
        begin: "2022-03-14",
        billable: true,
        description: "string",
        end: "2022-03-14",
        exported: true,
        fixedRate: 0,
        hourlyRate: 0,
        project: "1",
        tags: "string",
        user: 1,
      }]),
  });
});

afterEach(() => {
  jest.restoreAllMocks();
});

describe("Usuario", () => {
  it("loads a timesheet", async () => {
    await act(async () => render(<Usuario />));
    expect(screen.getAllByText("2022-03-14"));
  });
});
