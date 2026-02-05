import type { TurboModule } from 'react-native'
import { TurboModuleRegistry } from 'react-native'

export interface Spec extends TurboModule {
    clearMemoryCache: () => Promise<void>
    clearDiskCache: () => Promise<void>
}

export default TurboModuleRegistry.getEnforcing<Spec>('FastImageViewModule')
