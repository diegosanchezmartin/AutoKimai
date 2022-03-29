import { act, render, screen } from "@testing-library/react";
import React from "react";
import ModalResponse from "./ModalResponse";

beforeEach(() => {
  jest.spyOn(global, "fetch").mockResolvedValue({
    json: jest.fn().mockResolvedValue([
      {
        activity: 1,
        begin: "2022-03-25T08:00:00Z",
        end: "2022-03-25T14:00:00Z",
        id: 1,
        project: 1,
        user: 1
      },
    ]),
  });
});

afterEach(() => {
  jest.restoreAllMocks();
});

describe("ModalResponse", () => {
  it("loads registered schedules discovered and asks to continue or cancel", async () => {
    await act(async () => render(<ModalResponse />));
    expect(screen.getByText("2022-03-25T08:00:00Z"));
    expect(screen.getByText("2022-03-25T14:00:00Z"));
  });
});