import { act, render, screen } from "@testing-library/react";
import React from "react";
import ActivityButton from "./ActivityButton";

beforeEach(() => {
  jest.spyOn(global, "fetch").mockResolvedValue({
    json: jest.fn().mockResolvedValue([
        {
            parentTitle: "Ejemplo",
            project: 1,
            id: 1,
            name: "ActividadEjemplo",
            visible: true,
            metaFields: [],
            teams: [],
            color: null
        }
    ]),
  });
});

afterEach(() => {
  jest.restoreAllMocks();
});

describe("ActivityButton", () => {
  it("loads a activity button", async () => {
    await act(async () => render(<ActivityButton />));
    expect(screen.getByText("ActividadEjemplo"));
  });
});