import React from 'react';
import { render, screen, act } from '@testing-library/react';
import Usuario from './Usuario'

global.fetch = jest.fn(() => Promise.resolve({
    json: () => Promise.resolve({
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
    }),
}))

describe("Usuario", () => {
    it("loads a timesheet", async () => {
        await act(async() => render(<Usuario/>))
        expect(screen.getByText("2022-03-14")).toBeInTheDocument()
    })
})