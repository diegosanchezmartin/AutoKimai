import { act, render, screen } from "@testing-library/react";
import React from "react";
import ProjectButton from "./ProjectButton";

beforeEach(() => {
  jest.spyOn(global, "fetch").mockResolvedValue({
    json: jest.fn().mockResolvedValue([
      {
        parentTitle: "EjemploCustomer",
        customer: 1,
        id: 1,
        name: "Ejemplo",
        start: null,
        end: null,
        visible: true,
        metaFields: [],
        teams: [],
        color: null,
      },
    ]),
  });
});

afterEach(() => {
  jest.restoreAllMocks();
});

describe("ProjectButton", () => {
  it("loads a project button", async () => {
    await act(async () => render(<ProjectButton />));
    expect(screen.getByText("Ejemplo"));
  });
});
