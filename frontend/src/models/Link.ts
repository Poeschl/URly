export default interface Link {
  readonly id: number | undefined
  redirectPath: string
  originalUrl: string
  type: LinkType
  tracking: boolean
  defending: boolean
  annoyingDefender: boolean
  createdAt: Date
}

export const createEmptyLink = (): Link => {
  return {
    id: undefined,
    redirectPath: '',
    originalUrl: '',
    type: LinkType.SHORT,
    tracking: false,
    defending: false,
    annoyingDefender: false,
    createdAt: new Date()
  }
}

export enum LinkType {
  SHORT = "SHORT", LONG = "LONG"
}
